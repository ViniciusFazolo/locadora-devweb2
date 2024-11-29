import { Cliente } from "./cliente"
import { Dependente } from "./dependente"
import { Item } from "./item"
import { Socio } from "./socio"

export interface Locacao{
  id?: number,
  dtLocacao: string,
  dtDevolucaoPrevista: string,
  dtDevolucaoEfetiva: string,
  valorCobrado: number,
  multaCobrada: number,
  cliente: Cliente,
  item: Item
}
