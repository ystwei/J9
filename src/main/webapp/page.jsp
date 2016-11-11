 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    
<div class="pagination">
	<ul>
		<li>
			<c:if test="${pb.curPage==1}">

				<a href="#">首页</a>

			</c:if>
			<c:if test="${pb.curPage>1}">
				<a href="article?action=queryall&rootid=0&curpage=1">首页</a>

			</c:if>



		</li>
		<li>
			<c:if test="${pb.curPage==1}">

				<a href="#">前一页</a>

			</c:if>

			<c:if test="${pb.curPage>1}">
				<a href="article?action=queryall&rootid=0&curpage=${pb.curPage-1}">前一页</a></li>
			</c:if>
		
			<c:forEach begin="1" end="${pb.maxPage}" var="i">
				<li><a href="article?action=queryall&rootid=0&curpage=${i}">${i}</a></li>
			</c:forEach>


		<c:if test="${pb.curPage==pb.maxPage}">
		<li><a href="#">下一页</a></li>
		</c:if>
		<c:if test="${pb.curPage<pb.maxPage}">
			<li><a href="article?action=queryall&rootid=0&curpage=${pb.curPage+1}">下一页</a></li>

		</c:if>

		<c:if test="${pb.curPage==pb.maxPage}">
			<li>
				<a href="#">尾页</a>

			</li>
		</c:if>
		<c:if test="${pb.curPage<pb.maxPage}">
		<li>
			<a href="article?action=queryall&rootid=0&curpage=${pb.maxPage}">尾页</a>

		</li>
		</c:if>
	</ul>
</div>