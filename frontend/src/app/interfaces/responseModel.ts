export interface ResponseModelArray<T>{
    dados: T[],
    mensagem: string,
    status: boolean
}

export interface ResponseModelObject<T>{
    dados: T,
    mensagem: string,
    status: boolean
}