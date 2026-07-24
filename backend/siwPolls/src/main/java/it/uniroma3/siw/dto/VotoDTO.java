package it.uniroma3.siw.dto;

import jakarta.validation.constraints.NotNull;

public class VotoDTO {
	@NotNull
    private Long domandaId;
	@NotNull
    private Long opzioneId;

    public Long getDomandaId() { return domandaId; }
    public void setDomandaId(Long domandaId) { this.domandaId = domandaId; }
    
    public Long getOpzioneId() { return opzioneId; }
    public void setOpzioneId(Long opzioneId) { this.opzioneId = opzioneId; }
    
	public VotoDTO(Long domandaId, Long opzioneId) {
		this.domandaId = domandaId;
		this.opzioneId = opzioneId;
	}
	public VotoDTO() {
	}
}
