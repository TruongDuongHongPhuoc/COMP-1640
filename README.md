<h1> SPRING BOOT WEB APPLICATION </h1>

<h2> INTRODUCTION </h2>
the application use by Teacher and Student to managed and score student work.

<h2> Use case</h2>

![image](https://github.com/user-attachments/assets/76a2a7d8-2f37-4061-b7c2-97f635bd9e66)

<h2> Entity relationship diagram</h2>

![image](https://github.com/user-attachments/assets/bfda2768-8a3b-4773-b3e0-88bcf775024c)

<h2> Class Diagram</h2>

![image](https://github.com/user-attachments/assets/1115de8f-97c6-460d-9f33-4b6af37f2619)


<h2>Site Design</h2>

![dsadqwd](https://github.com/user-attachments/assets/b39e5f09-fc3b-45e4-9274-b4626271cf71)

<h2> Test case</h2>

<h2>Test Cases</h2>
    <table>
        <tr>
            <th>Date</th>
            <th>Test Number</th>
            <th>Purpose</th>
            <th>Process</th>
            <th>Expected Result</th>
            <th>Actual Result</th>
            <th>Pass/Fail</th>
            <th>Action Taken</th>
        </tr>
        <tr>
            <td>17/04/2024</td>
            <td>01</td>
            <td>Verify that the admin can create a new faculty</td>
            <td>1. Log in as an admin<br>2. Go to the admin page and "faculty management" section<br>3. Click on Create<br>4. Fill in details and submit</td>
            <td>A new faculty entry should be created and displayed</td>
            <td>As expected</td>
            <td>Pass</td>
            <td>No further action required</td>
        </tr>
        <tr>
            <td>17/04/2024</td>
            <td>02</td>
            <td>Verify faculty update information is displayed</td>
            <td>1. Log in as an admin<br>2. Go to "faculty management"<br>3. Select faculty<br>4. Click "update"</td>
            <td>Editable fields should be displayed</td>
            <td>As expected</td>
            <td>Pass</td>
            <td>No further action required</td>
        </tr>
        <tr>
            <td>17/04/2024</td>
            <td>03</td>
            <td>Verify that the admin can update faculty</td>
            <td>1. Log in as an admin<br>2. Go to "faculty management"<br>3. Select faculty<br>4. Modify details and save</td>
            <td>Faculty information should be updated</td>
            <td>As expected</td>
            <td>Pass</td>
            <td>No further action required</td>
        </tr>
        <tr>
            <td>17/04/2024</td>
            <td>04</td>
            <td>Verify that the admin can delete faculty</td>
            <td>1. Log in as an admin<br>2. Go to "faculty management"<br>3. Select faculty<br>4. Click "delete"</td>
            <td>Confirmation prompt, faculty deleted</td>
            <td>As expected</td>
            <td>Pass</td>
            <td>No further action required</td>
        </tr>
        <tr>
            <td>17/04/2024</td>
            <td>05</td>
            <td>Verify admin can create a new academic year</td>
            <td>1. Log in as an admin<br>2. Go to "academic year management"<br>3. Click "Create"<br>4. Fill details and submit</td>
            <td>A new academic year should be created</td>
            <td>As expected</td>
            <td>Pass</td>
            <td>No further action required</td>
        </tr>
      <tr>
              <td>17/04/2024</td>
              <td>06</td>
              <td>Verify that information is displayed when the admin goes to update the academic year page</td>
              <td>
                  1. Log in as an admin<br>
                  2. Go to "academic year management" section<br>
                  3. Select an academic year from the list<br>
                  4. Click on "update"
              </td>
              <td>Editable fields for the academic year details should be displayed for modification.</td>
              <td>As expected</td>
              <td>Pass</td>
              <td>No further action is required</td>
          </tr>
          <tr>
              <td>17/04/2024</td>
              <td>07</td>
              <td>Verify that the admin can update the academic year</td>
              <td>
                  1. Log in as an admin<br>
                  2. Go to "academic year management" section<br>
                  3. Select an academic year from the list<br>
                  4. Modify details<br>
                  5. Save the information
              </td>
              <td>Academic year information should be updated and saved successfully</td>
              <td>As expected</td>
              <td>Pass</td>
              <td>No further action is required</td>
          </tr>
          <tr>
              <td>17/04/2024</td>
              <td>08</td>
              <td>Verify that the admin can delete the academic year</td>
              <td>
                  1. Log in as an admin<br>
                  2. Go to "academic year management" section<br>
                  3. Select an academic year from the list<br>
                  4. Click on "delete."
              </td>
              <td>A confirmation prompt should appear asking to confirm the deletion. Upon confirmation, the academic year should be deleted</td>
              <td>As expected</td>
              <td>Pass</td>
              <td>No further action is required</td>
          </tr>
          <tr>
              <td>17/04/2024</td>
              <td>09</td>
              <td>Verify that all users with accounts can log out</td>
              <td>
                  1. Login as any role<br>
                  2. Find the "log out" button and click
              </td>
              <td>The user should be logged out, and the last session will be saved</td>
              <td>As expected</td>
              <td>Pass</td>
              <td>No further action is required</td>
          </tr>
          <tr>
              <td>17/04/2024</td>
              <td>10</td>
              <td>Verify that the unauthorized user can create a new guest account</td>
              <td>
                  1. Go to the registration page<br>
                  2. Fill in the username and password<br>
                  3. Submit the registration form
              </td>
              <td>A new user account should be created</td>
              <td>As expected</td>
              <td>Pass</td>
              <td>No further action is required</td>
          </tr>
            <tr>
          <td>17/04/2024</td>
          <td>11</td>
          <td>Verify that the user cannot create an account with a weak password</td>
          <td>
              1. Go to the registration page<br>
              2. Enter a weak password<br>
              3. Submit the registration form
          </td>
          <td>The system should display an error message indicating that the password is too weak.</td>
          <td>As expected</td>
          <td>Pass</td>
          <td>No further action is required</td>
      </tr>
      <tr>
          <td>17/04/2024</td>
          <td>12</td>
          <td>Verify that the admin can create an account for all roles in the system</td>
          <td>
              1. Login as admin<br>
              2. Admin navigates to the registration page<br>
              3. Enters account details<br>
              4. Submit the registration form
          </td>
          <td>A new high-priority account should be created</td>
          <td>As expected</td>
          <td>Pass</td>
          <td>No further action is required</td>
      </tr>
      <tr>
          <td>17/04/2024</td>
          <td>13</td>
          <td>Verify that the hello animation appeared when the account logs in for the first time</td>
          <td>
              1. Register a new account<br>
              2. Navigate to the login page<br>
              3. Enter username and password<br>
              4. Click "login"
          </td>
          <td>The user should be logged in successfully, and the animation should appear.</td>
          <td>As expected</td>
          <td>Pass</td>
          <td>No further action is required</td>
      </tr>
      <tr>
          <td>17/04/2024</td>
          <td>14</td>
          <td>Verify that all users can request to reset the password</td>
          <td>
              1. Go to the login page<br>
              2. Navigate to the "forgot password" page<br>
              3. Enter email address<br>
              4. Submit the form
          </td>
          <td>The user should receive an email with instructions to reset the password</td>
          <td>As expected</td>
          <td>Pass</td>
          <td>No further action is required</td>
      </tr>
      <tr>
          <td>17/04/2024</td>
          <td>15</td>
          <td>Verify that the user with an account can reset the password after receiving the email</td>
          <td>
              1. Go to the login page<br>
              2. Click the reset password link from the email<br>
              3. Enter a new password<br>
              4. Submit the form
          </td>
          <td>The user should be able to reset the password successfully</td>
          <td>As expected</td>
          <td>Pass</td>
          <td>No further action is required</td>
      </tr>
      <tr>
          <td>17/04/2024</td>
          <td>16</td>
          <td>Verify that information is protected by JWT (JSON Web Token)</td>
          <td>
              1. User attempts to make updates in edit source code from the browser<br>
              2. Open the developer tool in the browser and change the information.<br>
              3. Submit the changed information
          </td>
          <td>The system shouldn’t be able to use the information to process.</td>
          <td>As expected</td>
          <td>Pass</td>
          <td>No further action is required</td>
      </tr>
      <tr>
          <td>17/04/2024</td>
          <td>17</td>
          <td>Verify that all users with accounts can update account information</td>
          <td>
              1. Login as any role<br>
              2. Navigate to the "personal information" page<br>
              3. Edit account details<br>
              4. Save changes
          </td>
          <td>User account information should be updated successfully</td>
          <td>As expected</td>
          <td>Pass</td>
          <td>No further action is required</td>
      </tr>
      <tr>
          <td>17/04/2024</td>
          <td>18</td>
          <td>Verify that admin can set user role for account</td>
          <td>
              1. Login as admin<br>
              2. Admin navigates to the "set role" section<br>
              3. Select the user to modify the role<br>
              4. Sets new role and saves changes
          </td>
          <td>User role should be updated as per the admin's selection</td>
          <td>As expected</td>
          <td>Pass</td>
          <td>No further action is required</td>
      </tr>
      <tr>
          <td>17/04/2024</td>
          <td>19</td>
          <td>Verify that the marketing coordinator cannot change status after it is set to public</td>
          <td>
              1. Login as marketing coordinator<br>
              2. Navigate to the contribution<br>
              3. Attempt to change status after it is set to "public"
          </td>
          <td>The system should not allow changing the status after it is set to "public."</td>
          <td>As expected</td>
          <td>Pass</td>
          <td>No further action is required</td>
      </tr>
      <tr>
          <td>17/04/2024</td>
          <td>20</td>
          <td>Verify that student cannot create contribution when not filling in blanks</td>
          <td>
              1. Login as student<br>
              2. Navigate to the contribution creation page<br>
              3. Leave required fields blank<br>
              4. Submit the form
          </td>
          <td>The system should display an error message indicating that required fields cannot be blank.</td>
          <td>As expected</td>
          <td>Pass</td>
          <td>No further action is required</td>
      </tr>
      <tr>
    <td>17/04/2024</td>
    <td>21</td>
    <td>Verify that the student cannot create a contribution before accepting the terms</td>
    <td>
        1. Login as student<br>
        2. Attempt to create a contribution without accepting terms and conditions
    </td>
    <td>The system should only allow contribution creation if terms and conditions are accepted.</td>
    <td>As expected</td>
    <td>Pass</td>
    <td>No further action is required</td>
</tr>
<tr>
    <td>17/04/2024</td>
    <td>22</td>
    <td>Verify that the student cannot upload files except doc, docx</td>
    <td>
        1. Login as student<br>
        2. Attempt to upload a file that is not in doc or docx format
    </td>
    <td>The system should display an error message indicating that only doc and docx files are allowed.</td>
    <td>As expected</td>
    <td>Pass</td>
    <td>No further action is required</td>
</tr>
<tr>
    <td>17/04/2024</td>
    <td>23</td>
    <td>Verify that the student cannot create a contribution after the closure date</td>
    <td>
        1. Login as student<br>
        2. Attempt to create a contribution after the closure date
    </td>
    <td>The system should not allow contributions to be made after the closure date.</td>
    <td>As expected</td>
    <td>Pass</td>
    <td>No further action is required</td>
</tr>
<tr>
    <td>17/04/2024</td>
    <td>24</td>
    <td>Verify that admin can create within academic available</td>
    <td>
        1. Login as admin<br>
        2. Navigate to the admin page<br>
        3. Create a contribution within the available academic year
    </td>
    <td>The system should allow the creation of a contribution within the available academic year.</td>
    <td>As expected</td>
    <td>Pass</td>
    <td>No further action is required</td>
</tr>
<tr>
    <td>17/04/2024</td>
    <td>25</td>
    <td>Verify that the system sends mail to marketing coordinators with the same faculty</td>
    <td>
        1. Login as student<br>
        2. View student work<br>
        3. Submit a contribution
    </td>
    <td>The system should send an email notification to the marketing coordinator of the same faculty.</td>
    <td>As expected</td>
    <td>Pass</td>
    <td>No further action is required</td>
</tr>
<tr>
    <td>17/04/2024</td>
    <td>26</td>
    <td>Verify that the student can update when the academic year is available</td>
    <td>
        1. Login as student<br>
        2. Navigate to the contribution<br>
        3. Edit the contribution details<br>
        4. Save changes
    </td>
    <td>The system should allow contributions to be updated when the academic year is available.</td>
    <td>As expected</td>
    <td>Pass</td>
    <td>No further action is required</td>
</tr>
<tr>
    <td>17/04/2024</td>
    <td>27</td>
    <td>Verify that the student cannot update after the final close date</td>
    <td>
        1. Login as student<br>
        2. Attempt to update a contribution after the final close date
    </td>
    <td>The system should not allow updating a contribution after the final close date.</td>
    <td>As expected</td>
    <td>Pass</td>
    <td>No further action is required</td>
</tr>
<tr>
    <td>17/04/2024</td>
    <td>28</td>
    <td>Verify that the student can delete a contribution</td>
    <td>
        1. Login as student<br>
        2. Navigate to the view student work page<br>
        3. Click on "delete" any submitted contribution
    </td>
    <td>A confirmation prompt should appear asking to confirm the deletion.<br>Upon confirmation, the contribution should be deleted.</td>
    <td>As expected</td>
    <td>Pass</td>
    <td>No further action is required</td>
</tr>
<tr>
    <td>17/04/2024</td>
    <td>29</td>
    <td>Verify that student views their contribution</td>
    <td>
        1. Login as student<br>
        2. Navigate to the view work page
    </td>
    <td>The student should be able to view their contribution.</td>
    <td>As expected</td>
    <td>Pass</td>
    <td>No further action is required</td>
</tr>
<tr>
    <td>17/04/2024</td>
    <td>30</td>
    <td>Verify that the marketing coordinator views their student's contribution</td>
    <td>
        1. Login as marketing manager<br>
        2. Go to view the student page<br>
        3. Choose students to view their contribution.
    </td>
    <td>Marketing coordinator should be able to view the contributions of their students.</td>
    <td>As expected</td>
    <td>Pass</td>
    <td>No further action is required</td>
</tr>
        <!-- Add more test cases as needed -->
    </table>


<h2> Result</h2>

![image](https://github.com/user-attachments/assets/2c790308-12ec-49e4-90a6-e5ac229514c8)
![image](https://github.com/user-attachments/assets/17591675-e709-4a90-a059-e2a0a46d0ac9)
![image](https://github.com/user-attachments/assets/26a913dc-e159-44ba-ae69-92940075fab4)
![image](https://github.com/user-attachments/assets/4a5ef838-fdea-425e-aaa2-d1d6d798f7a2)
![image](https://github.com/user-attachments/assets/6013982a-21ae-4098-8a8b-3a5341cfa6b7)
![image](https://github.com/user-attachments/assets/108a83e8-deb5-46d6-bcd1-a6f982db312e)
![image](https://github.com/user-attachments/assets/848fbd49-a09f-4878-a2e5-91dae8ca3e0d)
![image](https://github.com/user-attachments/assets/9309e714-fc69-4dad-b402-e0560f3bc846)
![image](https://github.com/user-attachments/assets/d7ec8bb0-ca7a-4af3-ad9d-86fefa611756)
![image](https://github.com/user-attachments/assets/ed56dde2-acc5-432e-b144-b21ff99585fc)
![image](https://github.com/user-attachments/assets/cac3a4cb-3425-4162-9ea5-a025e9caad1a)
![image](https://github.com/user-attachments/assets/f457f161-048b-4b3b-8fda-e2067b92f4e9)
