angular.module("toDoList").factory("agendaAPI", function ($q, $http) {
	var agendasUrl = 'http://localhost:5000/agendas';

	var _obterAgendas = function() {
		var promessa = $q.defer();

		$http.get(agendasUrl)
			.then(function (resultado) {
				var agendas = resultado.data;

				promessa.resolve(agendas);
			});

		return promessa.promise;
	};

	var _salvarAgenda = function(agenda) {
		return $http.post(agendasUrl, agenda);
	};

	var _obterTarefas = function(agendaID) {
		var promessa = $q.defer();

		$http.get(agendasUrl + "/" + agendaID + "/tasks")
			.then(function (resultado) {
				var tarefas = resultado.data;

				promessa.resolve(tarefas);
			});

		return promessa.promise;
	};


	var _salvarTarefa = function(tarefa, agendaID) {
		var url = agendasUrl + "/" + agendaID + "/tasks";
		return $http.post(url, tarefa);
	}

	return {
		obterAgendas: _obterAgendas,
		salvarAgenda: _salvarAgenda,
		obterTarefas: _obterTarefas,
		salvarTarefa: _salvarTarefa
	};
});
