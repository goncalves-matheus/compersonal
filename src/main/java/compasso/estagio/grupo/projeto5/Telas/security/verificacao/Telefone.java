package compasso.estagio.grupo.projeto5.Telas.security.verificacao;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = TelefoneValidator.class)
@Documented
public @interface Telefone{
    String message() default "Telefone inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String numeroDoTelefone();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List{
        Telefone[] value();
    }
}