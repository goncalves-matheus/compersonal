package compasso.estagio.grupo.projeto5.Telas.security.verificacao;

import javax.validation.Payload;
import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CamposIguaisValidator.class)
@Documented
public @interface CamposIguais{
    String message() default "Os campos precisam ser iguais";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String primeiroCampo();
    String segundoCampo();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List{
        CamposIguais[] value();
    }
}