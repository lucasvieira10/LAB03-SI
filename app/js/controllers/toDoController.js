angular.module("toDoList", []);

angular.module("toDoList").controller("toDoListController", function($scope, agendaAPI) {
	var self = $scope;

	self.tarefasParaCumprir = 0;
	self.tarefasJaConcluidas = 0;

	self.agendas = [];
	self.tarefas = [];

	self.prioridades = [1, 2, 3, 4];

	var agendaAtual;

	self.carregarDadosDaAgendaAtual = function (agenda) {
		agendaAPI.obterTarefas(agenda.id).then(function (tarefas) {
			self.tarefas = tarefas;			
		});

		atualizarDadosDaAgendaAtual(agenda);
	};

	self.salvarAgenda = function(nome) {
		agendaAPI.salvarAgenda(new Agenda(nome, 0, 0)).then(carregarAgendas);
		delete self.agenda;
	};

	self.atualizarAgenda = function() {
		agendaAPI.atualizarAgenda(new Agenda(agendaAtual.nome, self.tarefasParaCumprir, 
			self.tarefasJaConcluidas), agendaAtual.id).then(atualizarTarefas);
	};

	self.removerAgenda = function() {
		agendaAPI.removerAgenda(agendaAtual.id).then(carregarAgendas);

		self.tarefas = [];
	};

	self.excluirAgendas = function () {
		agendaAPI.removerAgendas().then(carregarAgendas);
	};

	self.salvarTarefa = function(tarefa) {
		tarefa.id = Date.now();
		agendaAPI.salvarTarefa(tarefa, agendaAtual.id).then(carregarTarefas);

		self.tarefasParaCumprir++;

		delete self.tarefa;
		self.tarefaForm.$setPristine();
	};

	self.removerTarefa = function(tarefa) {
		agendaAPI.removerTarefa(agendaAtual.id, tarefa.id).then(carregarTarefas);
	};

	self.porcentagemDasTarefasConcluidas = function() {
		var porcentagem = (self.tarefasJaConcluidas / self.tarefas.length) * 100;
		return parseFloat(porcentagem.toFixed(1));
	};

	self.estaVazia = function(tarefas) {
		return (tarefas.length > 0);
	};

	self.limparTarefas = function() {
        self.tarefasParaCumprir = 0;
        self.tarefasJaConcluidas = 0;

        self.tarefas = [];

		agendaAPI.removerTarefas(agendaAtual.id).then(self.atualizarAgenda);
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

    var carregarAgendas = function () {
        agendaAPI.obterAgendas().then(function (agendas) {
            self.agendas = agendas;
        });
    };

    var atualizarDadosDaAgendaAtual = function (agenda) {
        self.tarefasParaCumprir = agenda.tarefasParaCumprir;
        self.tarefasJaConcluidas = agenda.tarefasJaConcluidas;

        agendaAtual = agenda;
    };

    var atualizarTarefas = function() {
        for (var i in self.tarefas) {
            agendaAPI.atualizarTarefa(self.tarefas[i], agendaAtual.id);
        }
    };

    var carregarTarefas = function () {
        agendaAPI.obterTarefas(agendaAtual.id).then(function (tarefas) {
            self.tarefas = tarefas;

            self.atualizarStatusDasTarefas();
        });
    };

    var tarefaEstaSelecionada = function(tarefa) {
        return tarefa.selecionada;
    };

	carregarAgendas();
});
