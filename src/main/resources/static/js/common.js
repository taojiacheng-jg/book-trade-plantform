/* =============================================
   全局自定义弹窗工具（替代原生 alert / confirm）
   依赖：Bootstrap 5 bundle（需先行加载）
   ============================================= */
(function () {
    var inited = false;

    function ensureModal() {
        if (inited) return;
        inited = true;

        // 消息弹窗（单按钮）
        var msgHtml =
            '<div class="modal fade" id="myMessageModal" tabindex="-1" aria-hidden="true">' +
            '<div class="modal-dialog modal-dialog-centered">' +
            '<div class="modal-content modal-brand">' +
            '<div class="modal-header"><h5 class="modal-title" id="myMessageModalTitle">提示</h5>' +
            '<button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>' +
            '<div class="modal-body" id="myMessageModalText" style="white-space:pre-wrap;"></div>' +
            '<div class="modal-footer"><button type="button" class="btn btn-primary" data-bs-dismiss="modal">知道了</button></div>' +
            '</div></div></div>';

        // 确认弹窗（双按钮）
        var confHtml =
            '<div class="modal fade" id="myConfirmModal" tabindex="-1" aria-hidden="true">' +
            '<div class="modal-dialog modal-dialog-centered">' +
            '<div class="modal-content modal-brand">' +
            '<div class="modal-header"><h5 class="modal-title" id="myConfirmModalTitle">确认操作</h5>' +
            '<button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>' +
            '<div class="modal-body" id="myConfirmModalText" style="white-space:pre-wrap;"></div>' +
            '<div class="modal-footer">' +
            '<button type="button" class="btn btn-outline-secondary" id="myConfirmCancel">取消</button>' +
            '<button type="button" class="btn btn-primary" id="myConfirmOk">确认</button>' +
            '</div></div></div>';

        $('body').append(msgHtml + confHtml);
    }

    // 消息提示：showToast('内容', '标题', 回调)
    function showToast(msg, title, callback) {
        ensureModal();
        $('#myMessageModalTitle').text(title || '提示');
        $('#myMessageModalText').text(msg);
        var el = document.getElementById('myMessageModal');
        if (callback && typeof callback === 'function') {
            $(el).off('hidden.bs.modal').on('hidden.bs.modal', callback);
        }
        new bootstrap.Modal(el).show();
    }

    // 确认弹窗：confirmDialog('内容','标题') -> Promise<boolean>
    function confirmDialog(msg, title) {
        ensureModal();
        $('#myConfirmModalTitle').text(title || '确认操作');
        $('#myConfirmModalText').text(msg);
        var el = document.getElementById('myConfirmModal');
        var modal = new bootstrap.Modal(el);
        return new Promise(function (resolve) {
            var done = false;
            $('#myConfirmOk').off('click').on('click', function () {
                if (done) return; done = true; modal.hide(); resolve(true);
            });
            $('#myConfirmCancel').off('click').on('click', function () {
                if (done) return; done = true; modal.hide(); resolve(false);
            });
            $(el).off('hidden.bs.modal').on('hidden.bs.modal', function () {
                if (done) return; done = true; resolve(false);
            });
            modal.show();
        });
    }

    window.showToast = showToast;
    window.confirmDialog = confirmDialog;
})();
