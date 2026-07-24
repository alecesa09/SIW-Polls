import apiRest from "./api";
import type { Sondaggio,Statistica, Commento, SondaggioDTO, SondaggioForm} from "../types";

export async function getStatistiche(codiceAccesso: string): Promise<Statistica[]>{
    const { data } = await apiRest.get<Statistica[]>(`/sondaggio/statistiche/${codiceAccesso}`);
    return data;
}

export async function getCommentiSondaggio(codiceAccesso: string): Promise<Commento[]>{
    const { data } = await apiRest.get<Commento[]>(`/sondaggio/commenti/${codiceAccesso}`);
    return data;
}
export async function ricercaPerNome(str:string):Promise<SondaggioDTO[]>{
    const { data } = await apiRest.get<SondaggioDTO[]>(`/sondaggio/search/${str}`);
    return data;
}

export async function ricercaPerCodiceAccesso(codiceAccesso:string):Promise<Sondaggio>{
    const { data } = await apiRest.get<Sondaggio>(`/sondaggio/search/codiceAccesso/${codiceAccesso}`);
    return data;
}

export async function ricercaSondaggiUtente():Promise<SondaggioDTO[]>{
    const { data } = await apiRest.get<SondaggioDTO[]>(`/sondaggio/utente`);
    return data;
}


export const creaSondaggio = async (sondaggio: SondaggioForm, file: File | null) => {
  const formData = new FormData();
  
  const sondaggioBlob = new Blob([JSON.stringify(sondaggio)], {
    type: "application/json"
  });

  formData.append("sondaggio", sondaggioBlob);

  if (file) {
    formData.append("file", file);
  }
  const response = await apiRest.post('/sondaggio', formData);
  return response.data;
};
