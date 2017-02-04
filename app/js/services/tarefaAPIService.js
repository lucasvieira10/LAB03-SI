angular.module("toDoList").factory("tarefaAPI", function ($q, $http) {
    var agendasUrl = 'http://localhost:5000/agendas';

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

    var _atualizarTarefa = function(tarefa, agendaID) {
        var url = agendasUrl + "/" + agendaID + "/tasks/" + tarefa.id;
        return $http.put(url, tarefa);
    };

    var _removerTarefas = function (agendaID) {
        var url = agendasUrl + "/" + agendaID + "/tasks";

        return $http.delete(url);
    };

    var _obterTarefasDasAgendas = function () {
        var url = agendasUrl + "/tasks";

        var promessa = $q.defer();

        $http.get(url)
            .then(function (resultado) {
                var tarefas = resultado.data;

                promessa.resolve(tarefas);
            });

        return promessa.promise;
    };

    return {
        obterTarefas: _obterTarefas,
        salvarTarefa: _salvarTarefa,
        removerTarefa: _removerTarefa,
        atualizarTarefa: _atualizarTarefa,
        removerTarefas: _removerTarefas,
        obterTarefasDasAgendas: _obterTarefasDasAgendas
    };
});
