package compasso.estagio.grupo.projeto5.Telas.dto;

public class AuxiliarRecuperaSenhaDto {

    public String email;
    public String codigo;

    public AuxiliarRecuperaSenhaDto(String email, String codigo) {
        this.email = email;
        this.codigo = codigo;
    }
    
    public RecuperaSenhaDto atualizarDto(RecuperaSenhaDto recuperaSenhaDto){
        recuperaSenhaDto.setEmail(this.email);
        recuperaSenhaDto.setCodigoEnviadoPorEmail(this.codigo);
        return recuperaSenhaDto;
    }

}
