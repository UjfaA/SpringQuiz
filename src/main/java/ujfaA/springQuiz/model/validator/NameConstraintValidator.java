package ujfaA.springQuiz.model.validator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.passay.IllegalCharacterRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.Rule;
import org.passay.RuleResult;

public class NameConstraintValidator implements ConstraintValidator<ValidName, String> {
	
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		
		Properties props = new Properties();
		try (var propertiesStream = new FileInputStream("src/main/resources/messages.properties")) {
			props.load(propertiesStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MessageResolver resolver = new PropertiesMessageResolver(props);
		List<Rule> rules = List.of( new IllegalCharacterRule( new char[]{'<','>','=',';',':','/','\\','*','+'}, true));
		PasswordValidator validator = new PasswordValidator( resolver, rules);
		
		RuleResult result = validator.validate(new PasswordData(password));
        
		if (result.isValid())
            return true;
        
        List<String> messages = validator.getMessages(result);
        
        String messageTemplate = messages.stream()
        		.collect(Collectors.joining(" "));
        context.buildConstraintViolationWithTemplate(messageTemplate)
        	.addConstraintViolation()
        	.disableDefaultConstraintViolation();
		return false;
	}

}
