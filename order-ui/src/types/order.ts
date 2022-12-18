export type Order = {
    id: string,
    description: string,
    user: {
        username: string
    },
    createdAt: string
}

export type OrderCreate = {
    description: string
}