angular.module("toDoList", []);

angular.module("toDoList").controller("toDoListController", function($scope, $http, agendaAPI) {
	var self = $scope;

	self.tarefasParaCumprir = 0;
	self.tarefasJaConcluidas = 0;

	self.agendas = [];

	self.tarefas = [];

	self.prioridades = [
		1, 2, 3, 4
	];

	var idAgendaAtual = "";

	var carregarAgendas = function () {
		agendaAPI.obterAgendas().then(function (agendas) {
			self.agendas = agendas;
		});
	};

	self.carregarTarefas = function (agendaID) {
		agendaAPI.obterTarefas(agendaID).then(function (tarefas) {
			self.tarefas = tarefas;
		});

		idAgendaAtual = agendaID;
	};

	self.salvarAgenda = function(nome) {
		var agenda = new Agenda(nome, 0, 0);

		agendaAPI.salvarAgenda(agenda).then(carregarAgendas);
		delete self.agenda;
	};

	self.salvarTarefa = function(tarefa) {
		tarefa.id = idAgendaAtual;
		agendaAPI.salvarTarefa(tarefa, idAgendaAtual).then(self.carregarTarefas(idAgendaAtual));

		self.tarefasParaCumprir++;	

		delete self.tarefa;
		self.tarefaForm.$setPristine();

		if (self.tarefas.length == 0) {
			self.carregarTarefas(idAgendaAtual);
		}
	};

	self.excluirAgendas = function () {
		self.agendas = [];
	}

	// METODOS PARA AJEITAR -------------------------------------------

	self.adicionarTarefa = function(tarefa) {
		self.tarefasParaCumprir++;	

		self.tarefas.push(tarefa);
		delete self.tarefa;
		self.tarefaForm.$setPristine();
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

	carregarAgendas();
	// carregarTarefas();
});
