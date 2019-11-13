<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<!-- Required Stylesheets -->
<link rel="stylesheet" type="text/css" href="../css/reset.css"
	media="screen" />
<link rel="stylesheet" type="text/css" href="../css/text.css"
	media="screen" />
<link rel="stylesheet" type="text/css"
	href="../css/fonts/ptsans/stylesheet.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../css/fluid.css"
	media="screen" />

<link rel="stylesheet" type="text/css" href="../css/mws.style.css"
	media="screen" />
<link rel="stylesheet" type="text/css" href="../css/icons/icons.css"
	media="screen" />

<!-- Demo and Plugin Stylesheets -->
<link rel="stylesheet" type="text/css" href="../css/demo.css"
	media="screen" />

<link rel="stylesheet" type="text/css"
	href="../plugins/colorpicker/colorpicker.css" media="screen" />
<link rel="stylesheet" type="text/css"
	href="../plugins/jimgareaselect/css/imgareaselect-default.css"
	media="screen" />
<link rel="stylesheet" type="text/css"
	href="../plugins/fullcalendar/fullcalendar.css" media="screen" />
<link rel="stylesheet" type="text/css"
	href="../plugins/fullcalendar/fullcalendar.print.css" media="print" />
<link rel="stylesheet" type="text/css" href="../plugins/tipsy/tipsy.css"
	media="screen" />
<link rel="stylesheet" type="text/css"
	href="../plugins/sourcerer/Sourcerer-1.2.css" media="screen" />
<link rel="stylesheet" type="text/css"
	href="../plugins/jgrowl/jquery.jgrowl.css" media="screen" />
<link rel="stylesheet" type="text/css"
	href="../plugins/spinner/spinner.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../css/jui/jquery.ui.css"
	media="screen" />

<!-- Theme Stylesheet -->
<link rel="stylesheet" type="text/css" href="../css/mws.theme.css"
	media="screen" />

<!-- JavaScript Plugins -->

<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>

<script type="text/javascript"
	src="../plugins/jimgareaselect/jquery.imgareaselect.min.js"></script>
<script type="text/javascript"
	src="../plugins/jquery.dualListBox-1.3.min.js"></script>
<script type="text/javascript" src="../plugins/jgrowl/jquery.jgrowl.js"></script>
<script type="text/javascript" src="../plugins/jquery.filestyle.js"></script>
<script type="text/javascript"
	src="../plugins/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript" src="../plugins/jquery.dataTables.js"></script>
<!--[if lt IE 9]>
<script type="text/javascript" src="../plugins/flot/excanvas.min.js"></script>
<![endif]-->
<script type="text/javascript" src="../js/layer/2.4/layer.js"></script>
<script type="text/javascript" src="../js/layui/layui.js"></script>
<script type="text/javascript" src="../js/layui/layui.all.js"></script>

<script type="text/javascript" src="../plugins/flot/jquery.flot.min.js"></script>
<script type="text/javascript" src="../js/layer/2.4/layer.js"></script>
<script type="text/javascript" src="../js/layui/layui.js"></script>
<script type="text/javascript" src="../js/layui/layui.all.js"></script>
<link rel="stylesheet" type="text/css" href="../js/layui/css/layui.css" media="screen" />
<script type="text/javascript"
	src="../plugins/flot/jquery.flot.pie.min.js"></script>
<script type="text/javascript"
	src="../plugins/flot/jquery.flot.stack.min.js"></script>
<script type="text/javascript"
	src="../plugins/flot/jquery.flot.resize.min.js"></script>
<script type="text/javascript"
	src="../plugins/colorpicker/colorpicker.js"></script>
<script type="text/javascript" src="../plugins/tipsy/jquery.tipsy.js"></script>
<script type="text/javascript"
	src="../plugins/sourcerer/Sourcerer-1.2.js"></script>
<script type="text/javascript" src="../plugins/jquery.placeholder.js"></script>
<script type="text/javascript" src="../plugins/jquery.validate.js"></script>
<script type="text/javascript" src="../plugins/jquery.mousewheel.js"></script>
<script type="text/javascript" src="../plugins/spinner/ui.spinner.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>

<script type="text/javascript" src="../js/mws.js"></script>
<script type="text/javascript" src="../js/demo.js"></script>
<script type="text/javascript" src="../js/themer.js"></script>
 <script type="text/javascript">
var xinwordListj = ${xinwordList};

function shower(num){
	$.ajax({
		url : "<%=basePath%>KfgdCon/selGdByNum.action", 
		async : false,
		dataType : "json",
		data : {
			"num":num,
		},
		success : function(data) {
			 layer.open({
			        type: 1,
			        title: "审核意见",
			        area: '500px',
			        offset: '220px',
			        content: $("#show").html()
			    });
			 $("#shr").val(data.shr);	
			 $("#shsj").val(data.shsj);
			 $("#shyj").val(data.shyj);
		
		}

	});
}
</script>
<script type="text/html" id="show">
    

     
        <div class="layui-form-item">
            <label class="layui-form-label">审核人</label>
            <div class="layui-input-block">
                <input style="width:200px" id="shr" autocomplete="off" class="layui-input" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">审核时间</label>
            <div class="layui-input-block">
                <input style="width:200px" id="shsj" autocomplete="off"  class="layui-input" 
                       />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">审核意见</label>
            <div class="layui-input-block">
                <textarea style="width:300px;height:100px"  rows="3" cols="15" id="shyj" autocomplete="off"  class="layui-input" 
                       />
            </div>
        </div>
        
       
    
</script>
<style>

	/* 固定表头 */
	.table-th-css {
		position: relative !important;
		top: 0;
	}
	
	/*修改按钮 */
	#xincreate_table_body .mws-table tbody .gradeX .xinjgd_show,
	#xincreate_table_body .mws-table tbody .gradeX .xinjgd_change,
	#xincreate_table_body .mws-table tbody .gradeX .xinjgd_del{
		border:none;
		background-color:rgb(60,61,61);
		height:24px;
		margin-right:6px;
		margin-top:2px;
		color:#fff;
		font-size: 12px;
		border-radius:4px;
	}
	
	/* 新增搜索DIV */
	.increase{
		display:block;
		width:99.8%;
		height:40px;
		background-color:#ccc;
		margin:0px auto;
		font-size:12px;
	}
	
	.increase p{
		height:40px;
	}
	
	.increase p select{
		width:120px;
		height:24px;
		border:none;
		border-radius:6px;
		margin-left:20px;
		padding-left:6px;
	}
	
	/* 新增搜索按钮 */
	#increase_btn,
	#xin_search_btn{
		margin-top:8px;
		margin-left:20px;
		border:none;
		background-color:rgb(60,61,61);
		width:100px;
		height:24px;
		margin-right:6px;
		color:#fff;
		border-radius:4px;
	}
	
	/* 新增修改弹出框 */
	#increase_word,
	#change_word{
		display:none;
		position: fixed;
		left: 0;
	    top:0;
	    right:0;
	    bottom:0;
	    z-index: 100; 
		background-color:rgba(0,0,0,0.6);
	}
	
	/* 新增修改弹出框关闭按钮 */
	#increase_word .close,
	#change_word .close{
		display:block;
		background-color:rgb(193,213,43);
		width:24px;
		height:24px;
		color: #fff;
    	border-radius: 13px;
		position:absolute;
		top:14px;
		right:20px;
		line-height: 24px;
   		text-align: center;
   		font-size: 18px;
	}
	#increase_word .close::before,
	#change_word .close::before {
	    content: "\2716";
	}
	
	/* 新增修改弹出框input */
	#increase_word .mws-form-row .increase_word_input,
	#change_word .mws-form-row .change_word_input {
		width:300px;
	}
	
	/* 新增修改弹出框label */
	#increase_word .mws-form-row label,
	#change_word .mws-form-row label{
		width:120px;
	}
	
	/* 新增修改弹出框确认按钮 */
	#increase_word #word_increase_btn,
	#change_word #word_change_btn{
		position:relative;
		top:10px;
		left:40%;
	}
	
	
	/* thead排序按钮 */
	.span-up{
        border-style: solid;
        border-width: 0px 5px 5px 5px;
        border-color: transparent transparent black transparent;
        width: 0px;
        height: 0px;
        display: block;
        position: absolute;
        top: 14px;
       	right: 6px;
    }
    
    .span-down{
        border-style: solid;
        border-width: 5px 5px 0px 5px;
        border-color: black transparent transparent transparent;
        width: 0px;
        height: 0px;
        display: block;
        position: absolute;
        top: 20px;
        right: 6px;
    }
	
	
</style>


 <script type="text/javascript">

/* var state = ${xinwordList};
alert(state); */

</script>
</head>
<body>

	<div id="increasedis" class="clearfix" style="overflow-x: hidden;">
	

		<div class="mws-panel grid_8 "
			style="width: 98%; padding-left: 12px; margin: 0px 0px 30px 0px; min-width:500px">
			<div class="mws-panel-header">
				<span class="mws-i-24 i-table-1">设备维护记录</span>
			</div>
			<div class="increase">
				<p>
					<input id="increase_btn" type="submit" value="新增" />
					<span style="font-size:20px;margin-left:20px;"> | </span>
					<select>
						<option value="全部">全部</option>
						<option value="未审核检修">未审核检修</option>
						<option value="已审核检修">已审核检修</option>
					</select>
					
					<input id="xin_search_btn" type="submit" value="搜索" />
				</p>
			</div>
			<div id="xincreate_table_body" class="mws-panel-body"
				style="overflow: auto !important; height: 600px;">
				<table class="mws-table" style="width:150%;">
					<thead>
						<tr>
							<th class="table-th-css">申请部门<span class="span-up"></span> <span class="span-down"></span></th>
							<th class="table-th-css">申请人<span class="span-up"></span> <span class="span-down"></span></th>
							<th class="table-th-css">申请时间<span class="span-up"></span> <span class="span-down"></span></th>
							<th class="table-th-css">检修内容<span class="span-up"></span> <span class="span-down"></span></th>
							<th class="table-th-css">审批人<span class="span-up"></span> <span class="span-down"></span></th>
							<th class="table-th-css">审批时间<span class="span-up"></span> <span class="span-down"></span></th>
							<th class="table-th-css">审批结果<span class="span-up"></span> <span class="span-down"></span></th>
							<th class="table-th-css">检修进程1</th>
							<th class="table-th-css">检修进程2<span class="span-up"></span> <span class="span-down"></span></th>
							<th class="table-th-css">检修结果<span class="span-up"></span> <span class="span-down"></span></th>
							
							<th class="table-th-css">处理</th>
						</tr>
					</thead>
					<tbody id="xinword_body">
						
				
					</tbody>
				</table>
			</div>
		</div>

		<div id="increase_word">
			<div class="mws-panel grid_4"
				style="width: 550px; min-width: 550px; margin: 100px 30%;">
				<div class="mws-panel-header">
					<span class="mws-i-24 i-pencil">新增检修作业票</span> <span class="close"></span>
				</div>
				<div class="mws-panel-body" style="height: 660px;">
					<form class="mws-form" action="" method="post">
						<div class="mws-form-inline">

							<div class="mws-form-row">
								<label>&emsp;&emsp;申请部门：</label>

								<div class="mws-form-item large">
									<input type="text" class="mws-textinput increase_word_input" 
										name="telephone" value="" autofocus="autofocus" />
								</div>
							</div>

							<div class="mws-form-row">
								<label>&emsp;&emsp;申请人：</label>

								<div class="mws-form-item large">
									<input type="text" class="mws-textinput increase_word_input"
										name="yhName" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label> &emsp;&emsp;申请时间：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput increase_word_input" readonly="readonly"
										id="tjTime" name="tjTime" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label>&emsp;&emsp;检修内容：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput increase_word_input"
										name="buildNo" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label>&emsp;&emsp;审批人：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput increase_word_input"
										name="cellNo" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label> &emsp;&emsp;审批时间：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput increase_word_input"
										name="houseNo" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label> &emsp;&emsp;审批结果：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput increase_word_input"
										name="houseNo" value="" />

								</div>
							</div>

							<div class="mws-form-row">
								<label> &emsp;&emsp;检修进程1：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput increase_word_input"
										name="tjName" value="" />
								</div>
							</div>
							
							<div class="mws-form-row">
								<label> &emsp;&emsp;检修进程2：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput increase_word_input"
										name="tjName" value="" />
								</div>
							</div>
							
							<div class="mws-form-row">
								<label> &emsp;&emsp;检修结果：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput increase_word_input"
										name="tjName" value="" />
								</div>
							</div>

							

						</div>
						<input type="button" id="word_increase_btn"
							class="mws-button black" value="新增" />
					</form>
				</div>
			</div>
		</div>
		
		
		<div id="change_word">
			<div class="mws-panel grid_4"
				style="width: 550px; min-width: 550px; margin: 100px 30%;">
				<div class="mws-panel-header">
					<span class="mws-i-24 i-pencil">审核/修改作业票</span> <span class="close"></span>
				</div>
				<div class="mws-panel-body" style="height: 660px;">
					<form class="mws-form" action=""  method="post">
						<div class="mws-form-inline">
						
						  
							
							
							<div class="mws-form-row">
								<label>&emsp;&emsp;申请部门：</label>

								<div class="mws-form-item large">
									<input type="text" class="mws-textinput change_word_input" 
										name="telephone" value="" autofocus="autofocus" />
								</div>
							</div>

							<div class="mws-form-row">
								<label>&emsp;&emsp;申请人：</label>

								<div class="mws-form-item large">
									<input type="text" class="mws-textinput change_word_input"
										name="yhName" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label> &emsp;&emsp;申请时间：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput change_word_input" readonly="readonly"
										id="tjTime" name="tjTime" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label>&emsp;&emsp;检修内容：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput change_word_input"
										name="buildNo" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label>&emsp;&emsp;审批人：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput change_word_input"
										name="cellNo" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label> &emsp;&emsp;审批时间：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput change_word_input"
										name="houseNo" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label> &emsp;&emsp;审批结果：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput change_word_input"
										name="tjName" value="" />
								</div>
							</div>

							<div class="mws-form-row">
								<label> &emsp;&emsp;检修进程1：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput change_word_input"
										name="tjName" value="" />
								</div>
							</div>
							
							<div class="mws-form-row">
								<label> &emsp;&emsp;检修进程2：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput change_word_input"
										name="tjName" value="" />
								</div>
							</div>
							
							<div class="mws-form-row">
								<label> &emsp;&emsp;检修结果：</label>
								<div class="mws-form-item large">
									<input type="text" class="mws-textinput change_word_input"
										name="tjName" value="" />
								</div>
							</div>

							
  							<input type="hidden" class="mws-textinput change_word_input"
							name="state" value="" />
						</div>
						<input type="button" id="word_change_btn"
							class="mws-button black" value="同意" />
						<input type="button" id="word_change_btn"
							class="mws-button black" value="拒绝" />
					</form>
				</div>
			</div>
		</div>
		
		
	</div>

</body>
<script type="text/javascript" src="../js/sbwhjl.js"></script>
</html>