package it.uniroma3.siw.dto;

public class StatisticheDTO {
	private Long domandaId;
	
	private Long opzioneId;
	
	private Long numeroVoti;

	public Long getDomandaId() {
		return domandaId;
	}

	public void setDomandaId(Long domandaId) {
		this.domandaId = domandaId;
	}

	public Long getOpzioneId() {
		return opzioneId;
	}

	public void setOpzioneId(Long opzioneId) {
		this.opzioneId = opzioneId;
	}

	public Long getNumeroVoti() {
		return this.numeroVoti;
	}

	public void setNumeroVoti(Long numeroVoti) {
		this.numeroVoti = numeroVoti;
	}

	public StatisticheDTO(Long domandaId, Long opzioneId, Long numeroVoti) {
		this.domandaId = domandaId;
		this.opzioneId = opzioneId;
		this.numeroVoti = numeroVoti;
	}

	@Override
	public String toString() {
		return "StatisticheDTO [DomandaId=" + domandaId + ", OpzioneId=" + opzioneId + ", NumeroVoti=" + numeroVoti
				+ "]";
	}
	
	 
	

	

}
