$(function () {
    $('#list').datagrid({
        loader: listLoader,
        title: '燃料电池数据列表',
        loadMsg: '数据加载中...',
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        columns: colModel,
        toolbar: '#toolbar'
    });
});

function listLoader(param, success, error) {
    var data = parent.getParams();
    var params = {
        plateNo: data['plateNo'],
        startDate: data['startDate'],
        endDate: data['endDate'],
        limit: param.rows,
        currentPage: param.page
    };
    page_list('page/ranliao_dianchi_list', params, success, error);
}

function doSearch() {
    $('#list').datagrid('reload');
}

function doDownload() {
    var queryForm = parent.$('#queryForm');
    if (!queryForm.form("validate")) {
        return;
    }

    var data = parent.getParams();
    queryForm.form('submit', {
        url: "download_msg",
        onSubmit: function(param) {
            param.plateNo = data['plateNo'];
            param.startDate = data['startDate'];
            param.endDate = data['endDate'];
        }
    });
}