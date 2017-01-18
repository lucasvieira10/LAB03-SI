var Agenda = function(nome, tarefasParaCumprir, tarefasJaConcluidas, tarefas) {
	var self = this;

	self.nome = nome;
	self.tarefasParaCumprir = tarefasParaCumprir;
	self.tarefasJaConcluidas = tarefasJaConcluidas;
	self.tarefas = tarefas;

	self.obterNome = function() {
		return self.nome;
	};

	self.alterarNome = function(nome) {
		self.nome = nome;
	};

	self.obterTarefasParaCumprir = function() {
		return self.tarefasParaCumprir;
	};

	self.alterarTarefasParaCumprir = function(tarefasParaCumprir) {
		self.tarefasParaCumprir = tarefasParaCumprir;
	};

	self.obterTarefasJaConcluidas = function() {
		return self.tarefasJaConcluidas;
	};

	self.alterarTarefasJaConcluidas = function(tarefasJaConcluidas) {
		self.tarefasJaConcluidas = tarefasJaConcluidas;
	};
};
