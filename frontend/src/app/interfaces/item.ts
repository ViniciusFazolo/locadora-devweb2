import { Titulo } from "./titulo"

export interface Item{
    id?: number,
    numSerie: number,
    dtAquisicao: string,
    tipoItem: string
    titulo: Titulo
}
