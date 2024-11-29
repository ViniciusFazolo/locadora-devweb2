import { Cliente } from "./cliente";
import { Socio } from "./socio";


export interface Dependente extends Cliente{
  socio: Socio
}
