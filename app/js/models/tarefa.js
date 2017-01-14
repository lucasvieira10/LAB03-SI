var Tarefa = function(id, nome, prioridade, selecionada) {
	var self = this;

	self.id = id;
	self.nome = nome;
	self.prioridade = prioridade;
	self.selecionada = selecionada;

	self.obterId = function() {
		return self.id;
	};

	self.obterNome = function() {
		return self.nome;
	};

	self.alterarNome = function(nome) {
		self.nome = nome;
	};

	self.obterPrioridade = function() {
		return self.prioridade;
	};

	self.alterarPrioridade = function(prioridade) {
		self.prioridade = prioridade;
	};

	self.obterSelecionada = function() {
		return self.selecionada;
	};

	self.alterarSelecionada = function(selecionada) {
		self.selecionada = selecionada;
	};
};
