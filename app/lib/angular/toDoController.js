angular.module("toDoList", []);

angular.module("toDoList").controller("toDoListController", function($scope) {
	var self = $scope;

	self.tarefasParaCumprir = 3;
	self.tarefasJaConcluidas = 0;

	self.tarefas = [
		// {nome: "Tomar café da manhã para ficar esperto."}, 
		// {nome: "Ir para o Embedded trabalhar."},
		// {nome: "Estudar Física Moderna para tentar terminar o curso."}
	];

	self.prioridades = [
		{tipo: 1},
		{tipo: 2},
		{tipo: 3},
		{tipo: 4}
	];

	self.adicionarTarefa = function(tarefa) {
		self.tarefasParaCumprir++;

		self.tarefas.push(angular.copy(tarefa));
		delete self.tarefa;
	};

	self.limparTarefas = function() {
		self.tarefas.splice(0, self.tarefas.length);

		self.tarefasParaCumprir = 0;
		self.tarefasJaConcluidas = 0;
	};

	self.removerTarefa = function(tarefa) {
		var indexTarefa = self.tarefas.indexOf(tarefa);
		self.tarefas.splice(indexTarefa, 1);

		self.atualizarStatusDasTarefas();		
	};

	self.porcentagemDasTarefasConcluidas = function() {
		var porcentagem = (self.tarefasJaConcluidas / self.tarefas.length) * 100;
		var porcentagemArredondada = parseFloat(porcentagem.toFixed(1));

		return porcentagemArredondada;
	}; 

	self.atualizarStatusDasTarefas = function() {
		self.tarefasParaCumprir = 0;
		self.tarefasJaConcluidas = 0;

		for (var i in self.tarefas) {
			if (tarefaEstaSelecionada(self.tarefas[i])) {
				self.tarefasJaConcluidas++;
			} else {
				self.tarefasParaCumprir++;
			}
		}
	};

	self.estaVazia = function(tarefas) {
		return (tarefas.length > 0);
	};

	var tarefaEstaSelecionada = function(tarefa) {
		return tarefa.selecionada;
	};
});
