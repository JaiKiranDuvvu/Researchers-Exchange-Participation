<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<script type="text/javascript" src="js/newstudy.js">
</script>
<%-- Code to display Page Name --%>
<h3 id="page_name">Adding a study</h3>
 <%-- Code to go Back to the Main Page  --%>
<a href="UserController?action=home&randId=<c:out value="${randId}"></c:out>" id="back_to_page">&laquo;Back to the Main Page</a>
<%-- Section to create new study --%>
<section style="margin-bottom: 100px;">
    <form id="studies_form" class="col-sm-10 col-sm-offset-1 form-horizontal" enctype="multipart/form-data" action="StudyController?action=add&randId=<c:out value="${randId}"></c:out>" method="post">
    
    	<div class="form-group">
        <label class="col-sm-4 control-label">Study Name *</label>
        <div class="col-sm-4">
        <input type="text" class="form-control" name="study_name" required id="study_name" />
         </div>
            </div>
        
        <div class="form-group">
        <label class="col-sm-4 control-label">Question Text *</label>
        <div class="col-sm-4">
        <input type="text" class="form-control" name="question_text" required id="question_text"/>
         </div>
            </div>
        
        <div class="form-group">
        <label class="col-sm-4 control-label">Image *</label>
        <div class="col-sm-4">
            <button id="browse_button" type="button" class="btn btn-primary">Browse</button>
            <div id="imageNameholder" style="float: right;"></div>
        <input type="file" id="browse_file" name="file" accept="image/*" style="display: none;">
         </div>
            </div>
        
        
        <div class="form-group">
        <label class="col-sm-4 control-label"># Participants *</label>
        <div class="col-sm-4">
        <input type="text" class="form-control" name="participant_text" required id="participants"/>
         </div>
            </div>
        
        <div class="form-group">
        <label class="col-sm-4 control-label"># Answers *</label>
        <div class="col-sm-4">
        <select name="answers" class="form-control" id="study_answers">
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select> <br>
         </div>
            </div>
        
        
        <div id="TextBoxContainer">
            <!-- Textboxes will be added here -->
	</div>
       
        
        <div class="form-group">
        <label class="col-sm-4 control-label">Description *</label>
        <div class="col-sm-4">
        <textarea name="description" class="form-control" required id="description"></textarea>
         </div>
            </div>
        
        <div class="form-group">
        <div class="col-sm-offset-5 col-sm-4">
        <button id="upload_button" type="submit"  class="btn btn-primary">Submit</button>
        <br/><br/><br/>
         </div>
            </div>
    </form>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>