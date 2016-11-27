<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<div id="contentwrapper">
    <div class="main_content">
        <%@include file="top.jsp" %>


        <div class="row-fluid">
            <div class="span12">
                <h3 class="heading">快来灌水</h3>


                <div class="alert alert-error">
                    <a class="close" data-dismiss="alert">×</a> <strong>
                    操作信息:${requestScope.info}${sessionScope.user==null?"":sessionScope.user.username}


                </strong>

                </div>

                <c:if test="${sessionScope.user!=null}">

                <div class="btn-group sepH_b">
                    <button data-toggle="dropdown" class="btn dropdown-toggle">
                        行数 <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">


                        <li><a href="UserControl?action=page&row=5&userid=${uid}">默认5行</a></li>
                        <li><a href="UserControl?action=page&row=10&userid=${uid}">每页10行</a></li>
                        <li><a href="UserControl?action=page&row=2&userid=${uid}">每页2行</a></li>
                    </ul>
                </div>
                </c:if>




                <table class="table table-bordered table-striped table_vam"
                       id="dt_gal">
                    <thead>
                    <tr>

                        <th class="table_checkbox">序号</th>
                        <th style="width: 50px">发布人</th>
                        <th style="width: 100px">主帖标题</th>
                        <th style="width: 420px">主帖内容</th>
                        <th style="width: 60px">发布日期</th>
                        <th style="width: 60px">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pb.data}" var="a" varStatus="status">

                    <tr>

                        <td>${status.count}</td>
                        <td><a href="upload/.jpg"

                               title="" class="cbox_single thumbnail">

                            <img src="user?action=pic&id=${a.user.id}"
                                 alt="" style="height: 50px; width: 50px" />

                        </a>



                        </td>
                        <td>


                            <a href="#rshow" title="灌水" data-toggle="modal"
                               id="my6" data-backdrop="static"
                               onclick="rshow(${a.id},${uid},${a.user.id});">
                                ${a.title}

                            </a>







                        </td>
                        <td>${a.content}</td>
                        <td>${a.datetime}</td>
                        <td>

                            <!-- 没登陆，游客 uid=0 -->
                            <c:if test="${sessionScope.user==null}">
                                <c:set var="uid" value="999"/>

                            </c:if>

                            <c:if test="${sessionScope.user!=null}">
                                <c:set var="uid" value="${sessionScope.user.id}"/>

                            </c:if>
                            <!-- 锚点传值 -->
                            <a href="#rshow" title="灌水" data-toggle="modal"
                               id="myp" data-backdrop="static"
                               onclick="rshow(${a.id},${uid},${a.user.id});">
                                <i class="icon-eye-open"></i>

                            </a>



                            <!-- 是本人贴可以删除和修改 -->

                             <c:if test="${sessionScope.user!=null && sessionScope.user.id==a.user.id}">

                                 <a
                                         href="article?action=del&id=${a.id}"
                                         title="删除本帖"><i class="icon-trash"></i></a>



                             </c:if>




                        </td>

                    </tr>
                    </c:forEach>
                    </tbody>
                </table>



            </div>
        </div>
        <c:if test="${pb.maxPage>1}">


            <%@include file="page.jsp"%>


        </c:if>



    </div>


</div>