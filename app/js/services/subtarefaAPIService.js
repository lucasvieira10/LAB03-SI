angular.module("toDoList").factory("subtarefaAPI", function ($q, $http) {
    var agendasUrl = 'http://localhost:5000/agendas';

    var _salvarSubtarefa = function (agendaID, tarefaID, subtarefa) {
        var url = agendasUrl + "/" + agendaID + "/tasks/" + tarefaID + "/subtasks";
        return $http.post(url, subtarefa);
    };

    var _carregarSubtarefas = function (agendaID, tarefaID) {
        var url = agendasUrl + "/" + agendaID + "/tasks/" + tarefaID + "/subtasks";

        var promessa = $q.defer();

        $http.get(url)
            .then(function (resultado) {
                var subtarefas = resultado.data;

                promessa.resolve(subtarefas);
            });

        return promessa.promise;
    };

    var _removerSubtarefa = function (agendaID, tarefaID, subtarefaID) {
        var url = agendasUrl + "/" + agendaID + "/tasks/" + tarefaID + "/subtasks/" + subtarefaID;
        return $http.delete(url);
    };

    var _atualizarSubtarefa = function(subtarefa, agendaID, tarefaID) {
        var url = agendasUrl + "/" + agendaID + "/tasks/" + tarefaID + "/subtasks/" + subtarefa.id;
        return $http.put(url, subtarefa);
    };

    return {
        salvarSubtarefa: _salvarSubtarefa,
        carregarSubtarefas: _carregarSubtarefas,
        removerSubtarefa: _removerSubtarefa,
        atualizarSubtarefa: _atualizarSubtarefa
    };
});
