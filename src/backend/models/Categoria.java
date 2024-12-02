package backend.models;

public enum Categoria {
    HIGIENE_PESSOAL,
    ALIMENTOS_NAO_PERECIVEIS,
    ALIMENTOS_CONGELADOS,
    ALIMENTOS_PERECIVEIS,
    BEBIDAS,
    HORTIFRUTI_E_CARNES,
    PRODUTOS_DE_LIMPEZA,
    PRODUTOS_PARA_PETS;

    // Caso precise de uma descrição mais amigável para a categoria
    public String getDescricao() {
        switch (this) {
            case HIGIENE_PESSOAL:
                return "Higiene Pessoal";
            case ALIMENTOS_NAO_PERECIVEIS:
                return "Alimentos Não Perecíveis";
            case ALIMENTOS_CONGELADOS:
                return "Alimentos Congelados";
            case ALIMENTOS_PERECIVEIS:
                return "Alimentos Perecíveis";
            case BEBIDAS:
                return "Bebidas";
            case HORTIFRUTI_E_CARNES:
                return "Hortifrúti e Carnes";
            case PRODUTOS_DE_LIMPEZA:
                return "Produtos de Limpeza";
            case PRODUTOS_PARA_PETS:
                return "Produtos para Pets";
            default:
                return "Categoria Desconhecida";
        }
    }
}
