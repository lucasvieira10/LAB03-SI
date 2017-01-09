angular.module("toDoList", []);

angular.module("toDoList").controller("toDoListController", function($scope, $http) {
	var self = $scope;

	self.tarefasParaCumprir = 0;
	self.tarefasJaConcluidas = 0;

	self.agendas = [
		{nome: "Graduação de computação"},
		{nome: "Trabalhar no embedded"}
	];

	self.tarefas = [];

	self.prioridades = [
		1, 2, 3, 4
	];

	var carregarTarefas = function() {
	    $http({
			method: 'GET',
			url: 'http://localhost:5000/tasks'
			}).then(function successCallback(response) {
	   	        $scope.tarefas = response.data;
	   	        console.log("get successfully");

			}, function errorCallback(response) {
	   	        console.log("get fail");
			});
	};

	var salvarTarefa = function(tarefa) {
		var params = $.param({ 'nome': tarefa.nome, 'prioridade': tarefa.prioridade, 'selecionada': false });

        $http({
        	method: 'POST',
            url: 'http://localhost:5000/tasks',
            data: params,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
         }).then(function successCallback(response) {
	   	        console.log("post successfully");

			}, function errorCallback(response) {
	   	        console.log("post fail");
			});;
	};

	self.adicionarTarefa = function(tarefa) {
		self.tarefasParaCumprir++;

		self.tarefas.push(angular.copy(tarefa));
		delete self.tarefa;
		self.tarefaForm.$setPristine();

		salvarTarefa(tarefa);
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

	carregarTarefas();
});
