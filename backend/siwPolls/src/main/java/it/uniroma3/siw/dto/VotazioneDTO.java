package it.uniroma3.siw.dto;
import java.util.List;

public class VotazioneDTO {
    private Long sondaggioId;
    private String visibilita;
    private List<VotoDTO> voti;

    public Long getSondaggioId() { return sondaggioId; }
    public void setSondaggioId(Long sondaggioId) { this.sondaggioId = sondaggioId; }
    
    public String getVisibilita() { return visibilita; }
    public void setVisibilita(String visibilita) { this.visibilita = visibilita; }
    
    public List<VotoDTO> getVoti() { return voti; }
    public void setVoti(List<VotoDTO> votazione) { this.voti = votazione; }
}
