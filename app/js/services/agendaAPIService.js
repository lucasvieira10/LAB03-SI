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
	};

	var _removerTarefa = function(agendaID, tarefaID) {
		var url = agendasUrl + "/" + agendaID + "/tasks/" + tarefaID;
		return  $http.delete(url);
	};

	var _removerAgendas = function() {
		return $http.delete(agendasUrl);
	};

	var _atualizarAgenda = function(agenda, agendaID) {
		var url = agendasUrl + "/" + agendaID;
		return $http.put(url, agenda);
	};

	var _atualizarTarefa = function(tarefa, agendaID) {
		var url = agendasUrl + "/" + agendaID + "/tasks/" + tarefa.id;
		return $http.put(url, tarefa);
	};

	var _removerAgenda = function(agendaID) {
		var url = agendasUrl + "/" + agendaID;
		return $http.delete(url);
	};

	var _removerTarefas = function (agendaID) {
		var url = agendasUrl + "/" + agendaID + "/tasks";

		return $http.delete(url);
    };

	return {
		obterAgendas: _obterAgendas,
		salvarAgenda: _salvarAgenda,
		obterTarefas: _obterTarefas,
		salvarTarefa: _salvarTarefa,
		removerTarefa: _removerTarefa,
		removerAgendas: _removerAgendas,
		atualizarAgenda: _atualizarAgenda,
		atualizarTarefa: _atualizarTarefa,
		removerAgenda: _removerAgenda,
		removerTarefas: _removerTarefas
	};
});
