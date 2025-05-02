Projeto simulando um sistema de uma biblioteca

(Projeto da faculdade)

Observações (regras gerais):
- Um usuário não pode ter mais de um empréstimo ativo
- Um usuário não realizar um empréstimo enquanto tiver alguma multa ativa
- Caso um usuário tenha alguma multa em um empréstimo, ele não poderá realizar (ou cancelar)
  empréstimo até pagar a multa
- Caso realize uma devolução e essa data seja superior a data prevista, será gerada uma multa
  para a realização da devolução, (o livro não é constado como devolvido até que a multa seja paga)
  
Usabilidades Adicionais (mais para teste do sistema):
- É possível começar um banco com dados (para usuário, funcionário, e livros), para facilitar os testes
  (não é gerado para o empréstimo)
  Basta alterar a variável "createDateBaseWithData" para true, assim será passado aos repositórios que gerarão os dados automaticamente
- É possível gerar devoluções atrasadas sem precisar alterar a data do computador
  basta alterar a variável "createDataDevolucaoEmprestimoToMulta" para true, ela irá perguntar para quantos dias
  após a data prevista que será realizada a devolução, e informe a quantidade de dias desejada
  Dessa forma é possível validar o valor da multa por dia excedido.
- Id's do livro são gerados a cada nova execução, ou seja diferente do usuário e funcionário, os ids vão mudar
  (mais por ser um UUID)
