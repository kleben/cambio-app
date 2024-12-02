# Sistema de Câmbio Real

## Regras do Sistema

O rei estabeleceu as seguintes regras para o sistema de câmbio:

1. **Moeda Base**: A moeda base do sistema é o **ouro real**.
2. **Cadastro de Produtos**: Todos os produtos serão cadastrados com valores expressos em **ouro real**.
3. **Limitação de Valores**: Nenhuma moeda terá valor maior que o **ouro real**.
4. **Taxas de Câmbio**:
   - Os valores das taxas de câmbio das moedas serão definidos em relação ao **ouro real**.
   - Por exemplo, se 1 **ouro real** equivale a 2,5 **tibares**, a taxa cadastrada para a moeda "tibar" será **2,5**.

## Taxas Adicionais

O rei também definiu taxas adicionais específicas para:
- Alguns materiais.
- Certos reinos.

## Estrutura do Projeto

Os scripts SQL necessários para configurar o banco de dados estão localizados na pasta **`db/migration`**.

O **Flyway** será responsável pela execução dos scripts iniciais durante o processo de migração do banco de dados.

---

**Nota**: Certifique-se de que os scripts SQL estejam devidamente configurados e que o Flyway esteja corretamente integrado ao projeto antes de executar o sistema.

