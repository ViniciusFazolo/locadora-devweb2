import { Locacao } from "./locacao"

export interface Cliente{
  id?: number,
  numInscricao: number,
  nome: string,
  dtNascimento: string,
  sexo: string,
  estahAtivo: boolean
  locacoes: Locacao[]
}
