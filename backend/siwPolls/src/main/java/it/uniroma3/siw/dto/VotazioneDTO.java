package it.uniroma3.siw.dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import it.uniroma3.siw.Votazione;

public class VotazioneDTO {
    private Long sondaggioId;
    private String visibilita;
    private List<VotoDTO> voti;
    
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
