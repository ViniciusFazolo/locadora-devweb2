import { Ator } from "./ator"
import { Classe } from "./classe"
import { Diretor } from "./diretor"
import { Item } from "./item"

export interface Titulo{
    id?: number,
    nome: string,
    ano: number,
    sinopse: string,
    categoria: string
    items: Item[],
    ator: Ator[],
    diretor: Diretor,
    classe: Classe
}
