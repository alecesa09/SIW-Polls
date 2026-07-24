package it.uniroma3.siw.dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import it.uniroma3.siw.Votazione;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VotazioneDTO {
	@NotNull	
    private Long sondaggioId;
	
	@NotNull
    private String visibilita;
	
	@NotNull(message = "La lista ddei voti non può essere omessa")
    private List<@Valid VotoDTO> voti;
    
    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    public VotazioneDTO(Long idSondaggio,Votazione votazione, List<VotoDTO> voti) {
    	sondaggioId=idSondaggio;
    	this.visibilita=votazione.getVisibilita().toString();
		this.voti = voti;
	}
    
    public VotazioneDTO() {
    	
    }
	public Long getSondaggioId() { return sondaggioId; }
    public void setSondaggioId(Long sondaggioId) { this.sondaggioId = sondaggioId; }
    
    public String getVisibilita() { return visibilita; }
    public void setVisibilita(String visibilita) { this.visibilita = visibilita; }
    
    public List<VotoDTO> getVoti() { return voti; }
    public void setVoti(List<VotoDTO> votazione) { this.voti = votazione; }
}
