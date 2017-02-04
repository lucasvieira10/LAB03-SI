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

	var _removerAgendas = function() {
		return $http.delete(agendasUrl);
	};

	var _atualizarAgenda = function(agenda, agendaID) {
		var url = agendasUrl + "/" + agendaID;
		return $http.put(url, agenda);
	};

	var _removerAgenda = function(agendaID) {
		var url = agendasUrl + "/" + agendaID;
		return $http.delete(url);
	};

	return {
		obterAgendas: _obterAgendas,
		salvarAgenda: _salvarAgenda,
		removerAgendas: _removerAgendas,
		atualizarAgenda: _atualizarAgenda,
		removerAgenda: _removerAgenda
	};
});
