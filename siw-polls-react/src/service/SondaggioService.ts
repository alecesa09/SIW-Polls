import api from "./api";
import type { Sondaggio, Votazione,Statistica, Commento, SondaggioDTO} from "../types";

export async function getSondaggio(id: string): Promise<Sondaggio> {
    const { data } = await api.get<Sondaggio>(`/sondaggio/${id}`);
    return data;
}
export async function postVoti(voti: Votazione): Promise<string> {
    const { data } = await api.post<string>('/sondaggio/voto', voti);
    return data;
}
export async function controllaPartecipazione(id: string): Promise<boolean> {
    const { data } = await api.get<boolean>(`/sondaggio/partecipazione/${id}`);
    return data;
}
export async function getStatistiche(id: string): Promise<Statistica[]>{
    const { data } = await api.get<Statistica[]>(`/sondaggio/statistiche/${id}`);
    return data;
}

export async function getCommenti(id: string): Promise<Commento[]>{
    const { data } = await api.get<Commento[]>(`/sondaggio/commenti/${id}`);
    return data;
}
export async function ricerca(str:string):Promise<SondaggioDTO[]>{
    const { data } = await api.get<SondaggioDTO[]>(`/sondaggio/search/${str}`);
    return data;
}

export async function ricercaPerCodiceAcesso(str:string):Promise<SondaggioDTO>{
    const { data } = await api.get<SondaggioDTO>(`/sondaggio/searchPriv/${str}`);
    return data;
}

export async function ricercaSondaggiUtente():Promise<SondaggioDTO[]>{
    const { data } = await api.get<SondaggioDTO[]>(`/sondaggio/utente`);
    return data;
}

export async function ricercaSondaggiVotatiUtente():Promise<SondaggioDTO[]>{
    const { data } = await api.get<SondaggioDTO[]>(`/sondaggi/votati/utente`);
    return data;
}