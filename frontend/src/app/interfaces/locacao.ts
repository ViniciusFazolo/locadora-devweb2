import { Cliente } from "./cliente"
import { Item } from "./item"

export interface Locacao{
  id?: number,
  dtLocacao: string,
  dtLocacaoPrevista: string,
  dtDevolucaoEfetiva: string,
  valorCobrado: number,
  multaCobrada: number,
  cliente: Cliente,
  item: Item
}
