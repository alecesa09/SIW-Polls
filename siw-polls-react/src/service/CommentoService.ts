import api from "./api";

export async function postCommento(sondaggioId: string, commento: string): Promise<string> {
    const { data } = await api.post<string>(
        `/sondaggio/commento/${sondaggioId}`, 
        commento, 
        {
            headers: {
                'Content-Type': 'text/plain'
            }
        }
    );
    return data;
}