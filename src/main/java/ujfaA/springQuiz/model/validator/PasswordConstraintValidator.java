package ujfaA.springQuiz.model.validator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RepeatCharacterRegexRule;
import org.passay.RuleResult;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
	
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
		
		PasswordValidator validator = new PasswordValidator(resolver, List.of(
				
				// Between 5 and 255 characters.
				new LengthRule(5, 255),
				
				// At least one digit character.
				new CharacterRule(EnglishCharacterData.Digit, 1),
				
				// No 3 or more repeating characters.
				new RepeatCharacterRegexRule(3)
				));
		
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
