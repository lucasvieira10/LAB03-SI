var Tarefa = function (nome, prioridade, selecionada, comentario, categoria, subtarefas) {
    var self = this;

    self.nome = nome;
    self.prioridade = prioridade;
    self.selecionada = selecionada;
    self.comentario = comentario;
    self.categoria = categoria;
    self.subtarefas = subtarefas;
};
