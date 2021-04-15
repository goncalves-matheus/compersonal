package compasso.estagio.grupo.projeto5.Telas.security.verificacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class CamposIguaisValidator implements ConstraintValidator<CamposIguais, Object> {

    private String primeiroCampo;
    private String segundoCampo;
    private String mensagem;

    @Override
    public void initialize(final CamposIguais constraintAnnotation) {
        primeiroCampo = constraintAnnotation.primeiroCampo();
        segundoCampo = constraintAnnotation.segundoCampo();
        mensagem = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valido = true;
        try {
            final Object primeiroObj = BeanUtils.getProperty(value, primeiroCampo);
            final Object segundoObj = BeanUtils.getProperty(value, segundoCampo);

            valido = primeiroObj == null && segundoObj == null || primeiroObj != null && primeiroObj.equals(segundoObj);
        } catch (final Exception ignore) {
        }

        if (!valido) {
            context.buildConstraintViolationWithTemplate(mensagem).addPropertyNode(primeiroCampo)
                    .addConstraintViolation().disableDefaultConstraintViolation();
        }
        return valido;
    }
}