$(function() {
	MainFrameJS.closeRight();
});


var MainFrameJS = (function() {
	var CUR_SEL_ID = null;// 当前选中节点标识
	var CUR_SEL_LEVEL = null;// 当前选中节点层级
	var CUR_SEL_HREF = null;// 当前节点链接
	var HIDDEN_LEFT = "T";// 是否隐藏左侧栏
	return {
		treeInitAfterAction : function() {
			$.tree.get("infoTree").expandByPath("-1");
		},
		treeTextClick : function(node) {
			if (null != node) {
				CUR_SEL_LEVEL = node.level;
				CUR_SEL_ID = node.id;
				CUR_SEL_HREF = node.href;
			}
			if (null != CUR_SEL_HREF) {
				$("#subFrame").attr("src", CUR_SEL_HREF);
				$("#subFrame").css("visibility", "visible");
			}
			

		},
		//
		hiddenLeft : function() {
			if (HIDDEN_LEFT == "T") {
				$("#testLayout").attr("style", "margin-left:-15.5%");
				HIDDEN_LEFT = "F";
			} else {
				$("#testLayout").attr("style", "margin-left:0");
				HIDDEN_LEFT = "T";
			}
		},
		// 禁用鼠标右键
        closeRight : function() {
            document.onkeydown = function() {
                if (event.keyCode == 116) {
                    event.keyCode = 0;
                    event.returnValue = false;
                }
            };
            document.oncontextmenu = function() {
                event.returnValue = false;
            };
        },
		initFrame : function() {
			var ifm = document.getElementById("subFrame");
			var subWeb = document.frames ? document.frames["subFrame"].document
					: ifm.contentDocument;
			if (ifm != null && subWeb != null) {
				ifm.height = subWeb.body.scrollHeight;
			}
		},
		dyniframesize : function(down) {
			var pTar = null;
			if (document.getElementById) {
				pTar = document.getElementById(down);
			} else {
				eval('pTar = ' + down + ';');
			}
			if (pTar && !window.opera) {
				// begin resizing iframe
				pTar.style.display = "block";
				if (pTar.contentDocument
						&& pTar.contentDocument.body.offsetHeight) {
					// ns6 syntax
					pTar.height = pTar.contentDocument.body.offsetHeight + 20;
					pTar.width = pTar.contentDocument.body.scrollWidth + 20;
				} else if (pTar.Document && pTar.Document.body.scrollHeight) {
					// ie5+ syntax
					pTar.height = pTar.Document.body.scrollHeight;
					pTar.width = pTar.Document.body.scrollWidth;
				}
			}
		}
	};
})();