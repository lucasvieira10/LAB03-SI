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

	var _salvarAgendas = function(agenda) {
		return $http.post(agendasUrl, agenda);
	};

	return {
		obterAgendas: _obterAgendas,
		salvarAgendas: _salvarAgendas
	};
});
