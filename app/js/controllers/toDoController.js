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

	var carregarAgendas = function () {
		agendaAPI.obterAgendas().then(function (agendas) {
			self.agendas = agendas;
		});
	};

	self.salvarAgenda = function(nome) {
		var agenda = new Agenda(nome, 0, 0);

		agendaAPI.salvarAgendas(agenda).then(carregarAgendas);
		delete self.agenda;
	};

	self.excluirAgendas = function () {
		self.agendas = [];
	}

	// METODOS PARA AJEITAR -------------------------------------------

	self.adicionarTarefa = function(tarefa) {
		self.tarefasParaCumprir++;

		var params = $.param({ 'id': '1231231231', 'nome': tarefa.nome, 'prioridade': tarefa.prioridade, 
			'selecionada': false });

        $http({
        	method: 'POST',
            url: 'http://localhost:5000/agendas/587a849023464514c52dbc0d/tasks',
            data: params,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        }).then(function successCallback(response) {
	   	        console.log("post successfully");

		}, function errorCallback(response) {
	   	        console.log("post fail");
		});

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
