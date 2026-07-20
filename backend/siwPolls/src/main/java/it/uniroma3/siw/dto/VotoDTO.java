package it.uniroma3.siw.dto;

public class VotoDTO {
    private Long domandaId;
    private Long opzioneId;

    public Long getDomandaId() { return domandaId; }
    public void setDomandaId(Long domandaId) { this.domandaId = domandaId; }
    
    public Long getOpzioneId() { return opzioneId; }
    public void setOpzioneId(Long opzioneId) { this.opzioneId = opzioneId; }
}
