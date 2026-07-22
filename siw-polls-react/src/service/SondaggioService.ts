import apiRest from "./api";
import type { Sondaggio,Statistica, Commento, SondaggioDTO, SondaggioForm} from "../types";

export async function getSondaggio(id: string): Promise<Sondaggio> {
    const { data } = await apiRest.get<Sondaggio>(`/sondaggio/${id}`);
    return data;
}

export async function getStatistiche(id: string): Promise<Statistica[]>{
    const { data } = await apiRest.get<Statistica[]>(`/sondaggio/statistiche/${id}`);
    return data;
}

export async function getCommentiSondaggio(id: string): Promise<Commento[]>{
    const { data } = await apiRest.get<Commento[]>(`/sondaggio/commenti/${id}`);
    return data;
}
export async function ricerca(str:string):Promise<SondaggioDTO[]>{
    const { data } = await apiRest.get<SondaggioDTO[]>(`/sondaggio/search/${str}`);
    return data;
}

export async function ricercaPerCodiceAcesso(str:string):Promise<SondaggioDTO>{
    const { data } = await apiRest.get<SondaggioDTO>(`/sondaggio/searchPriv/${str}`);
    return data;
}

export async function ricercaSondaggiUtente():Promise<SondaggioDTO[]>{
    const { data } = await apiRest.get<SondaggioDTO[]>(`/sondaggio/utente`);
    return data;
}

export async function creaSondaggio(sondaggio :SondaggioForm):Promise<string>{
    const { data } = await apiRest.post<string>(`/sondaggio`,sondaggio);
    return data;
}
