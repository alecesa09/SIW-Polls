import apiRest from "./api";

export async function postCommento(sondaggioId: string, commento: string): Promise<string> {
    const { data } = await apiRest.post<string>(
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