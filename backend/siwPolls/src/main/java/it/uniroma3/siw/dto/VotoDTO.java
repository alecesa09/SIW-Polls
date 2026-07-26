package it.uniroma3.siw.dto;

import java.util.Objects;

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
	@Override
	public int hashCode() {
		return Objects.hash(domandaId, opzioneId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VotoDTO other = (VotoDTO) obj;
		return Objects.equals(domandaId, other.domandaId) && Objects.equals(opzioneId, other.opzioneId);
	}
	
}
