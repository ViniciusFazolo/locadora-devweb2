import { Titulo } from "./titulo"

export interface Classe{
  id?: number,
  nome: string
  valor: number,
  prazoDevolucao: number,
  titulos: Titulo[]
}
