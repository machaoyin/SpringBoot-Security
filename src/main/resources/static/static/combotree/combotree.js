(function ($) {
	function getTree(url){
		var result=$.ajax({
			type : 'post',
			url : url ,
			dataType : 'json' ,
			async : false
		}).responseText;
		
		return eval('('+result+')');
	}
	$.fn.extend({
		bootstrapCombotree: function(options, param){
			var self=this;
			this.onBodyDown=function(event){
				var $options=undefined;
				try{
					$options=$.data($(event.target).prev()[0],'bootstrapCombotree');
				}catch (e) {
					$options=undefined;
				}
				if (!($options!=undefined || event.target.id == 'comboDiv' || $(event.target).parents("#comboDiv").length>0)) {
				//if (!(event.target.id == 'comboDiv' || $(event.target).parents("#comboDiv").length>0)) {
					self.hideMenu();
				}
			}
			this.hideMenu=function(){
				$('#comboDiv').fadeOut('fast');
				$("#comboDiv").remove();
				$("body").unbind("mousedown", self.onBodyDown);
			}
			
			this.getNode=function(root,id){
				for(var i=0;i<root.length;i++){
					var node=root[i];
					if(node.id==id){
						return node;
					}
					var x=self.getNode(node.nodes?node.nodes:[],id);
					if(x){
						return x;
					}
				}
				return null;
			},
			
			this.create=function(target){
				var $self=$(target);
				var $option=$.data(target,'bootstrapCombotree').options;
				$self.attr("type","hidden");
				var $this=$('<input class="form-control" />');
				$this.attr('placeholder',$self.attr('placeholder'));
				$this.attr('readonly',true);
				$self.after($this[0]);
				//取出options
				var options=$.extend({},$option,{
					onNodeSelected:function(event,node){
						$self.val(node.id);
						$this.val(node.text);
						$('#comboDiv').fadeOut('fast');
						$("#comboDiv").remove();
						$("body").unbind("mousedown", self.onBodyDown);

					}
				});
				//在显示的框中写入默认值对应的text的内容
				var $value=$self.val();
				if($value){
					var $node=self.getNode(options.data,$value);
					if($node){
						$this.val($node.text);
					}
				}
				
				$this.focus(function(){
					if($('#comboDiv').length>0){
						return;
					}
					var $div=$('<div class="panel panel-default combotree" id="comboDiv"><div class="panel-body"><div id="bootstrapCombotree"></div></div></div>');
					$div.appendTo($this.parent());
					$('#bootstrapCombotree').treeview(options);
					var thisObj=$(this);
					var thisOffset=$(this).position();
					var $dialogOffset=thisObj.closest('.modal-dialog').offset();
					var $left=thisOffset.left;
					var $top=thisOffset.top+thisObj.outerHeight();
					$div.css({
						left : $left+"px",
						top: $top+"px",
						zIndex: 1100
					}).slideDown('fast');
					$('body').bind("mousedown",self.onBodyDown);
				});
			};
			if (typeof options == 'string'){
				var method = $.fn.bootstrapCombotree.methods[options];
				if (method){
					return method(this, param);
				}
				return;
			}
			
			options = options || {};
			return this.each(function(){
				var state = $.data(this, 'bootstrapCombotree');
				if (state){
					$.extend(state.options, options);
				} else {
					$options=$.extend({}, $.fn.bootstrapCombotree.defaults, options);
					$options.thisObj=this;
					if($options.url){
						$options.data=getTree($options.url);
					}
					state = $.data(this, 'bootstrapCombotree', {
						options: $options,
					});
				}
				self.create(this);
			});
		}
	});
	
	$.fn.bootstrapCombotree.methods={
		options:function(jq){
			var $option=$.data(jq[0],'bootstrapCombotree').options;
			return $option;
		}
	}
	$.fn.bootstrapCombotree.defaults = {
			expandIcon: "glyphicon glyphicon-plus-sign",
		    collapseIcon: "glyphicon glyphicon-minus-sign",
			emptyIcon:"glyphicon glyphicon-file",
			showBorder:false,
			showTags: true,
			url:'combotree',
			data:[]
		};

})(jQuery)