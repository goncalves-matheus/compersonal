package compasso.estagio.grupo.projeto5.Telas.security.verificacao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class TelefoneValidator implements ConstraintValidator<Telefone, Object> {

    private String numeroDoTelefone;
    private String mensagem;

    @Override
    public void initialize(final Telefone constraintAnnotation) {
        numeroDoTelefone = constraintAnnotation.numeroDoTelefone();
        mensagem = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valido = false;
        try {
            final Object telefone = BeanUtils.getProperty(value, numeroDoTelefone);
            Pattern pattern = Pattern.compile("^((\\(\\d{2}\\))|\\d{2})[ ]?\\d{1}[ ]?\\d{4}[- ]?\\d{4}$");
            Matcher matcher = pattern.matcher(telefone.toString());
            if(matcher.matches() == true){
                valido = true;
            }
        } catch (final Exception ignore) {
        }

        if (!valido) {
            context.buildConstraintViolationWithTemplate(mensagem).addPropertyNode(numeroDoTelefone)
                    .addConstraintViolation().disableDefaultConstraintViolation();
        }
        return valido;
    }
}