/**
 * 공용 팝업(모달) 유틸
 * 브라우저 기본 alert()/confirm() 대신 사이트 디자인과 통일된 팝업을 띄운다.
 *
 *   await showAlert('저장했어요');            // 확인 버튼 하나
 *   const ok = await showConfirm('삭제할까요?'); // 취소/확인, ok = true/false
 *
 * - 팝업 DOM은 최초 호출 시 body에 한 번만 주입한다(템플릿마다 마크업 중복 불필요).
 * - 여러 줄 메시지는 '\n'으로 구분한다(CSS white-space:pre-line).
 */
(function () {
    let overlay, messageEl, footerEl;

    function ensureDom() {
        if (overlay) return;

        overlay = document.createElement('div');
        overlay.className = 'dialog-overlay';

        const card = document.createElement('div');
        card.className = 'dialog-card';

        messageEl = document.createElement('div');
        messageEl.className = 'dialog-message';

        footerEl = document.createElement('div');
        footerEl.className = 'dialog-footer';

        card.appendChild(messageEl);
        card.appendChild(footerEl);
        overlay.appendChild(card);
        document.body.appendChild(overlay);
    }

    function open() { overlay.classList.add('open'); }
    function close() { overlay.classList.remove('open'); }

    function makeButton(text, className, onClick) {
        const btn = document.createElement('button');
        btn.type = 'button';
        btn.className = className;
        btn.textContent = text;
        btn.onclick = onClick;
        return btn;
    }

    /**
     * 확인 버튼 하나짜리 안내 팝업
     * @param {string} message 표시할 메시지 (여러 줄은 '\n')
     * @param {{okText?: string}} [opts]
     * @returns {Promise<void>} 확인을 누르면 resolve
     */
    window.showAlert = function (message, opts) {
        opts = opts || {};
        return new Promise((resolve) => {
            ensureDom();
            messageEl.textContent = message;
            footerEl.innerHTML = '';
            const okBtn = makeButton(opts.okText || '확인', 'btn-modal-save', () => {
                close();
                resolve();
            });
            footerEl.appendChild(okBtn);
            open();
            okBtn.focus();
        });
    };

    /**
     * 취소/확인 두 버튼짜리 확인 팝업
     * @param {string} message 표시할 메시지 (여러 줄은 '\n')
     * @param {{okText?: string, cancelText?: string}} [opts]
     * @returns {Promise<boolean>} 확인=true, 취소=false
     */
    window.showConfirm = function (message, opts) {
        opts = opts || {};
        return new Promise((resolve) => {
            ensureDom();
            messageEl.textContent = message;
            footerEl.innerHTML = '';
            const cancelBtn = makeButton(opts.cancelText || '취소', 'btn-modal-cancel', () => {
                close();
                resolve(false);
            });
            const okBtn = makeButton(opts.okText || '확인', 'btn-modal-save', () => {
                close();
                resolve(true);
            });
            footerEl.appendChild(cancelBtn);
            footerEl.appendChild(okBtn);
            open();
            okBtn.focus();
        });
    };
})();
